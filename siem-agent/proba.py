import win32evtlog
from datetime import datetime, timedelta

server = 'localhost' # name of the target computer to get event logs
logtype = 'Security'
hand = win32evtlog.OpenEventLog(server,logtype)
flags = win32evtlog.EVENTLOG_BACKWARDS_READ|win32evtlog.EVENTLOG_SEQUENTIAL_READ
total = win32evtlog.GetNumberOfEventLogRecords(hand)

last_log_timestamp = datetime.now()
print(last_log_timestamp)
print('TIMESTAMP|SOURCE NAME|COMPUTER NAME|ID|EVENT TYPE|')
flag = False

while True:
    if flag:
        break
    hand = win32evtlog.OpenEventLog(server, logtype)
    events = win32evtlog.ReadEventLog(hand, flags,0)

    if events:
        for event in events:
            if event.TimeGenerated > last_log_timestamp:
                print("%s |%s | %s | %s | %s |" % (event.TimeGenerated, event.SourceName, event.ComputerName, event.EventID, event.EventType))
                last_log_timestamp = event.TimeGenerated
            else:
                #flag = True
                flag = False


win32evtlog.CloseEventLog(hand)