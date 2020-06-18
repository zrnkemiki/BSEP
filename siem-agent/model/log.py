class Log:
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        self.timestamp = timestamp
        self.level = level
        self.event_id = event_id
        self.computer_name = computer_name
        self.source_name = source_name
        self.message = message


class WindowsLog(Log):
    def __init__(self, timestamp, level='', event_id='', computer_name='', source_name='', message=''):
        super().__init__(timestamp, level, event_id, computer_name, source_name, message)


