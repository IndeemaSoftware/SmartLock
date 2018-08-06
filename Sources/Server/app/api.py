from app import app
from flask import abort, request, jsonify
import requests
from app import door
import json


@app.route('/open_door', methods=['POST'])
def open_door():
    content = request.json

    print(content)
#    print('- {}'.format(content['api_key']))

    response = requests.get("https://your_server/users/current.json",
                            auth=(format(content['api_key']), "random_pass"))

    if response.status_code == 200:
        print("Open Door...Send logical 1 to Raspberry")
        door.open_door()

    data = {}
    data['status'] = response.ok
    data['code'] = response.status_code
    json_data = json.dumps(data)

    return json_data


