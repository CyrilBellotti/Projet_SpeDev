#!/usr/bin/env python
import pika
import json

connection = pika.BlockingConnection(
    pika.ConnectionParameters(host='172.20.10.11', credentials=pika.PlainCredentials("cyril", "azerty")))
channel = connection.channel()

channel.queue_declare(queue='actionDevice', durable="false")

class Test(object):
    def __init__(self, data):
        self.__dict__ = json.loads(data)


def callback(ch, method, properties, body):
    toto = json.loads(body)
    test1 = Test(body)
    print " [x] Received an action. The",test1.sensor,"sensor is on the position", test1.actionEvent, "."


channel.basic_consume(
    queue='actionDevice', on_message_callback=callback, auto_ack=True)

print(' [*] Waiting for actions.')
channel.start_consuming()
