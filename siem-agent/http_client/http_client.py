import requests


def post_method(data):
    # trebace header (sertifikat)
    r = requests.post('https://httpbin.org/post', data=data)