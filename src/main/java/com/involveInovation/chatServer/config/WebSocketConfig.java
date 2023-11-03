package com.involveInovation.chatServer.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
//Esta anotación significa que la aplicación puede admitir comunicación en tiempo real bidireccional entre el servidor y
// los clientes a través de WebSockets
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //esta config es para recibir un msj del cliente. Esto es para la url del controlador

        //Establece el prefijo para conectarse con el servidor, cuando el cliente envía
        // un msj y este debe ser procesado, para matchear con un método del controlador
        // se debe usar primero el /app para ubicarse en el controlador
        registry.setApplicationDestinationPrefixes("/app");

        // Los dos que siguen tienen que ver con enviar msj desde el servidor al cliente. Para usar a estos hay que
        //suscribirse

        //Prefijo que sirve para enviar mensajes privados o públicos, se debe poner primero /user o /chatroom
        //para escuchar los mensajes enviados por el servidor.
        //Esto se utiliza para enviar mensajes desde el servidor a los clientes suscritos.
        // Los clientes pueden suscribirse a estos destinos y recibirán mensajes enviados a través de ellos.
        registry.enableSimpleBroker("/chatroom","/user");

        //Prefijo de destino de usuario: Este prefijo se
        // utiliza para la comunicación de uno a uno con un usuario específico. Es para los msj privados
        // Los mensajes dirigidos a usuarios específicos se enviarán a destinos que comiencen con "/user".
        registry.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //Todos los que quieran establecer una conexión con este webSocket deberán establecer la URL con 'ws'
        //Es decir para establecer la conexión será http:localhost:8080/ws. Se permite que cualquiera se pueda suscribir
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }
}
