import requests


def post_method(data):
    # trebace header (sertifikat)
    r = requests.post('http://localhost:9005/post-logs', json=data)
    print(r)