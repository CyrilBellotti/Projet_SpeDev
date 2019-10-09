#!/usr/bin/env python
import pika
import json
import random
import time

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='172.20.10.11', credentials=pika.PlainCredentials("cyril", "azerty")))
channel = connection.channel()

channel.exchange_declare(exchange='atlantis-exchange', durable='true', exchange_type="fanout")


listUid = ["Kitchen", "Garage", "Device1", "Device2", "Device3"];

i = 0;

while i < 10000:
    message2 = {
    "uid": random.choice(listUid),
    "sensors": [
            {
            "type": "Temperature",
            "value": random.randint(-30,40),
            "uid": "TempKitchen"
            },
            {
            "type": "Luminosity",
            "value": random.randint(40,150),
            "uid": "LumKitchen"
            }
        ]
    }
    channel.basic_publish(exchange='atlantis-exchange', routing_key='', properties=pika.BasicProperties(content_type="application/json"), body=json.dumps(message2))
    print(" [x] Sent sensor with value")
    time.sleep(5);
    i = i + 1
connection.close()


