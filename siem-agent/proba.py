import win32evtlog

server = 'localhost' # name of the target computer to get event logs
logtype = 'System'
hand = win32evtlog.OpenEventLog(server,logtype)
flags = win32evtlog.EVENTLOG_BACKWARDS_READ|win32evtlog.EVENTLOG_SEQUENTIAL_READ
total = win32evtlog.GetNumberOfEventLogRecords(hand)

while True:
    events = win32evtlog.ReadEventLog(hand, flags,0)
    print('CATEGORY|TIMESTAMP|SOURCE|ID|TYPE|DATA')
    if events:
        for event in events:
            print(dir(event))
            for_print = str(event.EventCategory) + '|' + str(event.TimeGenerated) + '|' + str(event.SourceName) + '|' +\
                        str(event.EventID) + '|' + str(event.EventType) + '|'
            data = event.StringInserts
            if data:
                por = ""
                #for msg in data:
                    #print(msg)
            print("%s |%s | %s | %s | %s | %s" % (event.EventCategory, event.TimeGenerated, event.SourceName, event.EventID, event.EventType, por))
    break
win32evtlog.CloseEventLog(hand)