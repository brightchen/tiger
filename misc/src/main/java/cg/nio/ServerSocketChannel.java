package cg.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSocketChannel {
  private static final Logger logger = LoggerFactory.getLogger(ServerSocketChannel.class);

  private ByteBuffer buffer = ByteBuffer.allocate(102400);
  
  public void create(int port) throws IOException {
    final AsynchronousServerSocketChannel listener = AsynchronousServerSocketChannel.open()
        .bind(new InetSocketAddress(port));

    listener.accept(null, new CompletionHandler<AsynchronousSocketChannel, Void>() {
      public void completed(AsynchronousSocketChannel ch, Void att) {
        // accept the next connection
        listener.accept(null, this);

        // handle this connection
        handleNewConnection(ch);
      }

      public void failed(Throwable exc, Void att) {
        logger.error("failed. " + exc.getMessage());
      }
    });
  }
  
  public void handleNewConnection(AsynchronousSocketChannel channel)
  {
    channel.read(buffer);
    buffer.clear();
  }
}
