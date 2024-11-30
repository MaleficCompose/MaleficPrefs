package xyz.malefic.serialize

import java.io.*

/** Utility object for serialization and deserialization of objects. */
object SerializationUtil {
  /**
   * Serialize an object to a byte array.
   *
   * @param obj the object to serialize
   * @return the byte array representation of the object
   * @throws IOException if an I/O error occurs
   */
  fun serialize(obj: Any?): ByteArray {
    ByteArrayOutputStream().use { bos ->
      ObjectOutputStream(bos).use { oos ->
        oos.writeObject(obj)
        return bos.toByteArray()
      }
    }
  }

  /**
   * Deserialize an object from a byte array.
   *
   * @param data the byte array to deserialize
   * @param clazz the class of the object to deserialize
   * @return the deserialized object
   */
  fun <T : Serializable> deserialize(data: ByteArray, clazz: Class<T>): T? {
    ByteArrayInputStream(data).use { bis ->
      ObjectInputStream(bis).use { ois ->
        return clazz.cast(ois.readObject())
      }
    }
  }
}
