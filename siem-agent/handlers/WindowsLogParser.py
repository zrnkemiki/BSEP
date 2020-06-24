import os
import win32evtlog
from model.log_level import SeverityLevel
from model.log import WindowsLog
from exceptions.OSException import OSException
from http_client.http_client import post_method
from threading import Thread
import time
from datetime import datetime, timedelta


class WindowsLogParser(Thread):
    def __init__(self, regex_filter=None, interval=None, kind='Security'):
        if os.name != 'nt':
            raise OSException('This parser works only on Windows machine!')

        self.regex_filter = regex_filter
        self.interval = interval
        # ova dva bih mogao citati iz configa isto
        # ili mozda ipak ni ne treba
        self.server = 'localhost'  # name of the target computer to get event logs
        self.logtype = kind
        self.hand = win32evtlog.OpenEventLog(self.server, self.logtype)
        self.flags = win32evtlog.EVENTLOG_BACKWARDS_READ | win32evtlog.EVENTLOG_SEQUENTIAL_READ
        self.total = win32evtlog.GetNumberOfEventLogRecords(self.hand)
        Thread.__init__(self)
        self.last_log_timestamp = datetime.now()


    def read_and_parse(self):
        flag = False
        while True:
            if flag:
                break
            self.hand = win32evtlog.OpenEventLog(self.server, self.logtype)
            events = win32evtlog.ReadEventLog(self.hand, self.flags, 0)
            w_logs = []
            if events:
                for event in events:
                    if event.TimeGenerated > self.last_log_timestamp:
                        msg_data = event.StringInserts
                        if msg_data:
                            msg = ' '.join(msg_data)
                        else:
                            msg = ''
                        w_log = WindowsLog(str(event.TimeGenerated), SeverityLevel(event.EventType).name, event.EventID,
                                           event.ComputerName, event.SourceName, msg)
                        print(w_log.format_json())
                        w_logs.append(w_log.format_json())
                        self.last_log_timestamp = event.TimeGenerated
                    else:
                        flag = True

        win32evtlog.CloseEventLog(self.hand)

        return w_logs

    # overrides Thread run
    # send logs
    def run(self):
        while True:
            parsed_logs = self.read_and_parse()
            if len(parsed_logs) != 0:
                response = post_method(parsed_logs)
                print(response, '[ [Windows OS] ', len(parsed_logs), ' LOGS SENT TO CENTER]')
            if self.interval != -1:
                time.sleep(self.interval)
