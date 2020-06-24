class Log:
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        self.timestamp = timestamp
        self.level = level
        self.eventId = event_id
        self.computerName = computer_name
        self.sourceName = source_name
        self.message = message

    def format_json(self):
        return self.__dict__


class WindowsLog(Log):
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        super().__init__(timestamp, level, event_id, computer_name, source_name, message)


