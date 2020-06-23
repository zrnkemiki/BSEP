import sys
import requests

TRUSTSTORE_PATH = sys.argv[2]
CLIENT_CERT_PATH = sys.argv[3]
CLIENT_PK_PATH = sys.argv[4]


def post_method(data):
    # trebace header (sertifikat)
    r = requests.post('https://localhost:9005/post-logs', json=data,
                      verify=TRUSTSTORE_PATH,
                      cert=(CLIENT_CERT_PATH,
                            CLIENT_PK_PATH))
    return r
