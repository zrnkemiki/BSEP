from threading import Thread
import os


class LinuxLogParser(Thread):
    def __init__(self, regex_filter=None, interval=None, log_path=None):
        self.regex_filter = regex_filter
        self.interval = interval
        self.path = log_path

        if os.path.isdir(log_path):
            self.path_type = "dir"
        else:
            self.path_type = "file"

