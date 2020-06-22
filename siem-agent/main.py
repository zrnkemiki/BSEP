import json
import sys
from handlers.WindowsLogParser import WindowsLogParser
from handlers.AppLogParser import AppLogParser
from exceptions.ConfigException import ConfigException


def check_config(config):
    for src in conf['sources']:
        if src['type'] not in ['OS Windows', 'OS Linux', 'Application', 'Web-Server']:
            raise ConfigException('Allowed types are: [OS Windows, OS Linux, Application, Web-Server]!')
        if not isinstance(src['interval'], int) and not (isinstance(src['interval'], float)):
            raise ConfigException('Interval has to be float or int!')


if __name__ == '__main__':
    config_file = sys.argv[1]

    with open(config_file) as f:
        conf = json.load(f)

    check_config(conf)

    parsers = []
    for source in conf['sources']:
        if source['type'] == 'OS Windows':
            win_parser = WindowsLogParser(source['filter'], source['interval'])
            parsers.append(win_parser)
        elif source['type'] == 'OS Linux':
            continue
        else:
            app_parser = AppLogParser(source['filter'], source['interval'], source['path'])
            parsers.append(app_parser)

    for p in parsers:
        p.start()
        #p.run_simple()
        #p.run_simple()
        #p.run_simple()
        #p.run_simple()
        #p.run_simple()
