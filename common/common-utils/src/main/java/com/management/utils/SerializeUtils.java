package com.management.utils;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class SerializeUtils {

    private static Logger logger = LoggerFactory.getLogger(SerializeUtils.class);

    public static Object deserialize(byte[] bytes) {
        Object result = null;
        if(isEmpty(bytes)){
            return null;
        }
        try{
            ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
            try{
                ObjectInputStream objectInputStream = new ObjectInputStream(byteStream);
                try{
                    result = objectInputStream.readObject();
                }catch (ClassNotFoundException ex){
                    throw new Exception("Failed to deserialize Object type", ex);
                }
            }catch (Throwable te) {
                throw new Exception("Failed to deserialize", te);
            }
        }catch (Exception e){
            logger.error("Failed to deserialize ", e);
        }

        return result;
    }

    public static boolean isEmpty(byte[] data) {
        return (data == null || data.length == 0);
    }

    /**
     * 序列化
     * @param object
     * @return
     */
    public static byte[] serialize(Object object) {
        byte[] result = null;
        if (object == null) {
            return new byte[0];
        }
        try {
            ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
            try {
                if (!(object instanceof Serializable)) {
                    throw new IllegalArgumentException(SerializeUtils.class.getSimpleName() + "requires a Serializable payload but received an object of type [" + object.getClass().getName() + "]");
                }
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteStream);
                objectOutputStream.writeObject(object);
                objectOutputStream.flush();
                result = byteStream.toByteArray();
            } catch (Throwable e) {
                throw new Exception("Failed to Serialize", e);
            }
        }catch (Exception e) {
            logger.error("Failed to Serialize", e);
        }
        return result;
    }


}