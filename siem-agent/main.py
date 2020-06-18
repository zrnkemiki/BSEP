import json
import sys
from handlers.WindowsLogParser import WindowsLogParser

if __name__ == '__main__':
    config_file = sys.argv[1]

    with open(config_file) as f:
        conf = json.load(f)

    print(conf['sources'])

    parsers = []
    for source in conf['sources']:
        if source['type'] == 'OS Windows':
            win_parser = WindowsLogParser(source['filter'], source['interval'])
            parsers.append(win_parser)
        elif source['type'] == 'OS Linux':
            a = 3
        else:
            a = 1