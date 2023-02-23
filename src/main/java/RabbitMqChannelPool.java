import com.rabbitmq.client.Channel;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;


public class RabbitMqChannelPool extends GenericObjectPool<Channel> {

    public RabbitMqChannelPool(PooledObjectFactory<Channel> factory) {
        super(factory);
    }

    public RabbitMqChannelPool(PooledObjectFactory<Channel> factory, GenericObjectPoolConfig config) {
        super(factory, config);
    }

    public RabbitMqChannelPool(PooledObjectFactory<Channel> factory, GenericObjectPoolConfig config, AbandonedConfig abandonedConfig) {
        super(factory, config, abandonedConfig);
    }
}

