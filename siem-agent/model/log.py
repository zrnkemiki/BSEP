class Log:
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        self.timestamp = timestamp
        # 8 - Audit Success
        # 16 - Audit Failure
        # ostali su ugl sistemski (info, warning, error itd)
        self.level = level
        self.eventId = event_id
        # masina koja je uzrokovala log
        self.computerName = computer_name
        # source koji je upisao log (windows security auditing npr)
        self.sourceName = source_name
        self.message = message

    def format_json(self):
        return self.__dict__


class WindowsLog(Log):
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        super().__init__(timestamp, level, event_id, computer_name, source_name, message)


