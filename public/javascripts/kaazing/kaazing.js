function setupKaazing(url,onMessage) {
	
    var stompConnectionFactory = new StompConnectionFactory(url);

    var connectionFuture = stompConnectionFactory.createConnection("","", function () {
        try {
                var connection = connectionFuture.getValue();

                var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                
                var topic = session.createTopic("/topic/LocationTopic");
                var consumer = session.createConsumer(topic);
                consumer.setMessageListener(onMessage);
                
                connection.start(function () { 
               		console.log("Connected to the WS Gateway") 
                    }
                );

            } catch (e) {
                console.log(e)
            }
        });

}

function setupKaazingQueue(url,onQueueMessage,userId) {
	
    var stompConnectionFactory = new StompConnectionFactory(url);

    var connectionFuture = stompConnectionFactory.createConnection("","", function () {
        try {
                var connection = connectionFuture.getValue();

                var session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
                
                var queueName = "queue."+userId
                
                var topic = session.createQueue(queueName);
                
                var consumer = session.createConsumer(topic);
                
                console.log("Connected queue : "+queueName)
                
                consumer.setMessageListener(onQueueMessage);
                
                connection.start(function () { 
               		console.log("Connected to the WS Gateway for Queue") 
                    }
                );

            } catch (e) {
                console.log(e)
            }
        });

}