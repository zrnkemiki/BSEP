import json
import sys
from handlers.WindowsLogParser import WindowsLogParser
from handlers.AppLogParser import AppLogParser
from exceptions.ConfigException import ConfigException
import os

'''
argv[1] = path to config.json
argv[2] = path to truststore file
argv[3] = path to client certificate file
argv[4] = path to client private key file
'''


def check_config(config):
    for src in conf['sources']:
        if src['type'] not in ['OS Windows', 'OS Linux', 'Application', 'Web-Server']:
            raise ConfigException('Allowed types are: [OS Windows, OS Linux, Application, Web-Server]!')
        if not isinstance(src['interval'], int) and not (isinstance(src['interval'], float)):
            raise ConfigException('Interval has to be float or int!')
        if src['type'] == 'OS Windows':
            if src['kind'] not in ['Security', 'System']:
                raise ConfigException('Kind has to be "Security" or "System"')


def handle_dir(path):
    files = []
    if os.path.isdir(path):
        for r, d, ff in os.walk(path):
            for file in ff:
                if '.log' in file:
                    files.append(os.path.join(r, file))
    else:
        files.append(path)

    return files


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
            file_list = handle_dir(source['path'])
            for i in file_list:
                app_parser = AppLogParser(source['filter'], source['interval'], i)
                parsers.append(app_parser)

    for p in parsers:
        p.start()
