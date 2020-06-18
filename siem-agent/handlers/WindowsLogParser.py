import os
import win32evtlog
from model.log_level import SeverityLevel
from model.log import WindowsLog
from exceptions.OSException import OSException


class WindowsLogParser:
    def __init__(self, regex_filter=None, interval=None):
        if os.name != 'nt':
            raise OSException('This parser works only on Windows machine!')

        self.regex_filter = regex_filter
        self.interval = interval
        # ova dva bih mogao citati iz configa isto
        self.server = 'localhost'  # name of the target computer to get event logs
        self.logtype = 'System'
        self.hand = win32evtlog.OpenEventLog(self.server, self.logtype)
        self.flags = win32evtlog.EVENTLOG_BACKWARDS_READ | win32evtlog.EVENTLOG_SEQUENTIAL_READ
        self.total = win32evtlog.GetNumberOfEventLogRecords(self.hand)

    def read_and_parse(self):
        events = win32evtlog.ReadEventLog(self.hand, self.flags, 0)
        w_logs = []
        if events:
            for event in events:
                msg_data = event.StringInserts
                if msg_data:
                    msg = ' '.join(msg_data)
                else:
                    msg = ''
                w_log = WindowsLog(event.TimeGenerated, SeverityLevel[event.EventType], event.EventID,
                                   event.ComputerName, event.SourceName, msg)

                w_logs.append(w_log)

        win32evtlog.CloseEventLog(self.hand)
        return w_logs
