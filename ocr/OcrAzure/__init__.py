import logging
import requests
import json
import azure.functions as func


def main(req: func.HttpRequest) -> str:
    logging.info('OCR function has been triggered and will process a request.')

    subscription_key = ''  # TODO fill it up
    computer_vision_endpoint = ''  # TODO fill it up

    orc_endpoint = computer_vision_endpoint + "vision/v2.1/ocr"

    image_url = req.params.get('url')

    headers = {'Ocp-Apim-Subscription-Key': subscription_key}
    params = {'language': 'unk', 'detectOrientation': 'true'}
    data = {'url': image_url}

    response = requests.post(orc_endpoint, headers=headers, params=params, json=data)

    response.raise_for_status()

    analysis = response.json()

    parsed = json.loads(json.dumps(analysis))

    result = list()

    for region in parsed["regions"]:
        for line in region["lines"]:
            for word in line["words"]:
                result.extend([word["text"]])

    return " ".join(result)
