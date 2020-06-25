from enum import Enum


class SeverityLevel(Enum):
    LOG_ALWAYS = 0
    CRITICAL = 1
    ERROR = 2
    WARNING = 3
    INFORMATIONAL = 4
    VERBOSE = 5
    SUCCESS_AUDIT = 8
    FAILURE_AUDIT = 16
