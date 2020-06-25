from threading import Thread
from datetime import datetime
from sys import platform
from exceptions.OSException import OSException
from model.log import Log
from http_client.http_client import post_method
import time


class LinuxLogParser(Thread):
    def __init__(self, regex_filter=None, interval=None, log_path=None):
        if platform != 'linux' and platform != 'linux2':
            raise OSException('This parser works only on Linux machine!')
        self.regex_filter = regex_filter
        self.interval = interval
        self.path = log_path
        Thread.__init__(self)
        self.last_log_timestamp = datetime.now()

    def read_and_parse(self):
        f = open(self.log_path, "r")
        log_file = f.read()

        logs = []
        lines = log_file.splitlines(keepends=False)
        for line in lines:
            log_elements = line.split()
            unparsed = log_elements[0] + " " + log_elements[1]
            timestamp = self.parse_timedate(unparsed)
            if timestamp >= self.last_log_timestamp:
                msg = ' '.join(log_elements[6:])
                log = Log(str(timestamp), log_elements[2], log_elements[3], log_elements[4], log_elements[5], msg)
                logs.append(log.format_json())
                self.last_log_timestamp = timestamp
                continue
            break
        return logs

    def parse_timedate(self, timestamp):
        return datetime.strptime(timestamp, '%Y-%m-%d %H:%M:%S')


    def run(self):
        while True:
            parsed_logs = self.read_and_parse()
            if len(parsed_logs) != 0:
                response = post_method(parsed_logs)
                print(response, '[ [LinuxParser] ', len(parsed_logs), ' LOGS SENT TO CENTER]')
            if self.interval != -1:
                time.sleep(self.interval)



