import os
import requests


def post_method(data):
    # trebace header (sertifikat)
    r = requests.post('https://localhost:9005/post-logs', json=data,
                      verify='C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-agent\\certificates\\truststore\\root-ca.pem',
                      cert=('C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-agent\\certificates\\client_cert\\client-signed.cer',
                            'C:\\Users\\Laptop\\Documents\\GitHub\\BSEP\\siem-agent\\certificates\\client_cert\\client-private.key'))
    return r
