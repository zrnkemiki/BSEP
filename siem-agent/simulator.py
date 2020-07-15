import sys
from exceptions.ArgumentException import ArgumentException
import time
from datetime import datetime

'''
start:
argv[1] = {attack, normal}
'''


def generate_ok_logs():
    time.sleep(1)
    current = datetime.now()
    without_date = ' INFORMATIONAL 507 RS-VJ1 Microsoft-Windows-Kernel-Power EnergyDrain{0} DripsResidencyInUs{1855196107} DisconnectedStandby {false}\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l1 = formated_date + without_date
    time.sleep(3)
    current = datetime.now()
    without_date = ' WARNING 9999 RS-VJ1 LOGIN-SECURITY {user: id=1361361; too many login requests}\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l2 = formated_date + without_date
    time.sleep(2)
    current = datetime.now()
    without_date = ' FAILURE_AUDIT 4625 RS-VJ3 Microsoft-Windows-Security-Auditing S-1-5-18 DESKTOP-RS-VJ3$ WORKGROUP 0x3e7 S-1-0-0 Laptop DESKTOP-RS-VJ3 0xc000006d %%2313 0xc000006a 2 User32  Negotiate DESKTOP-RS-VJ3 - - 0 0xae8 C:\\Windows\\System32\\svchost.exe 127.0.0.1 0\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l3 = formated_date + without_date

    logs = [l1, l2, l3]

    log_path = "C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-agent\\logs\\simulated\\sim.log"
    with open(log_path, 'a') as ff:
        for ll in logs:
            #print(ll)
            ff.write(ll)


# same credentials - failed
def generate_many_lrqst():
    current = datetime.now()
    without_date = ' WARNING 9999 RS-VJ1 LOGIN-SECURITY {user: id=1361361; too many login requests}\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l1 = formated_date + without_date
    return [l1, l1, l1, l1]


# error log
def generate_error_log():
    current = datetime.now()
    without_date = ' ERROR 11085 RS-VJ2 DCOM application-specific Local Activation {2593F8B9-4EAF-457C-B68A-50F6B8EA6B54} {15C20B67-12E7-4BB6-92BB-7AFF07997402} DESKTOP-JOL1HA4 Laptop S-1-5-21-3356151724-1799670505-223934021-1001 LocalHost (Using LRPC) Unavailable Unavailable\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l1 = formated_date + without_date
    return [l1]


# login failed - same computer
def generate_bad_credentials():
    current = datetime.now()
    without_date = ' FAILURE_AUDIT 4625 RS-VJ3 Microsoft-Windows-Security-Auditing S-1-5-18 DESKTOP-RS-VJ3$ WORKGROUP 0x3e7 S-1-0-0 Laptop DESKTOP-RS-VJ3 0xc000006d %%2313 0xc000006a 2 User32  Negotiate DESKTOP-RS-VJ3 - - 0 0xae8 C:\\Windows\\System32\\svchost.exe 127.0.0.1 0\n'
    formated_date = current.strftime('%Y-%m-%d %H:%M:%S')
    l1 = formated_date + without_date
    return [l1, l1, l1, l1]


def generate_attack_logs():
    logs = []
    logs.extend(generate_many_lrqst())
    time.sleep(1)
    logs.extend(generate_error_log())
    time.sleep(1)
    logs.extend(generate_bad_credentials())
    time.sleep(1)

    log_path = "C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-agent\\logs\\simulated\\sim.log"
    with open(log_path, 'a') as ff:
        for l in logs:
            ff.write(l)


if __name__ == '__main__':
    opt = sys.argv[1]

    if opt == 'normal':
        for i in range(30):
            generate_ok_logs()
    elif opt == 'attack':
        for i in range(30):
            generate_attack_logs()
            time.sleep(2)
    else:
        raise ArgumentException('Allowed: {normal, attack}')


