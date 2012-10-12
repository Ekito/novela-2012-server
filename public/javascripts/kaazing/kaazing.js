function setupKaazing(url, onMessage, onQueueMessage, userId) {

	var stompConnectionFactory = new StompConnectionFactory(url);

	var connectionFuture = stompConnectionFactory.createConnection("", "",
			function() {
				try {
					var connection = connectionFuture.getValue();

					var session = connection.createSession(false,
							Session.AUTO_ACKNOWLEDGE);

					var topic = session.createTopic("/topic/LocationTopic");
					var consumer = session.createConsumer(topic);
					consumer.setMessageListener(onMessage);

					
					if (userId != null) {
						var queueName = "queue." + userId

						var queue = session.createQueue(queueName);

						var qConsumer = session.createConsumer(queue);

						console.log("Connected queue : " + queueName)

						qConsumer.setMessageListener(onQueueMessage);
					}

					connection.start(function() {
						console.log("Connected to the WS Gateway")
					});

				} catch (e) {
					console.log(e)
				}
			});

}