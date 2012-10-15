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

					if (userId != null && userId.length > 0){
						var queueName = "/queue/" + userId

						var queue = session.createQueue(queueName);

						var consumerQ = session.createConsumer(queue);
						
						consumerQ.setMessageListener(onQueueMessage);

						console.log("Connected queue : " + queueName)
					}
					
					connection.start(function() {
						console.log("Connected to the WS Gateway")
					});

				} catch (e) {
					console.log(e)
				}
			});

}

//function setupKaazingQueue(url, onQueueMessage, userId) {
//
//	var stompConnectionFactory = new StompConnectionFactory(url);
//
//	var connectionFuture = stompConnectionFactory.createConnection("", "",
//			function() {
//				try {
//					var connection = connectionFuture.getValue();
//
//					var session = connection.createSession(false,
//							Session.AUTO_ACKNOWLEDGE);
//
//					var queueName = "/queue/" + userId
//
//					var queue = session.createQueue(queueName);
//
//					var consumer = session.createConsumer(queue);
//
//					console.log("Connected queue : " + queueName)
//
//					consumer.setMessageListener(onQueueMessage);
//
//					connection.start(function() {
//						console.log("Connected to the WS Gateway for Queue")
//					});
//
//				} catch (e) {
//					console.log(e)
//				}
//			});
//}