package space.song.datastore.serializer

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.google.protobuf.InvalidProtocolBufferException
import space.song.datastore.proto.AppPreferences
import java.io.InputStream
import java.io.OutputStream
import javax.inject.Inject

/**
 * An [androidx.datastore.core.Serializer] for the [AppPreferences] proto.
 */
class AppPreferencesSerializer @Inject constructor() : Serializer<AppPreferences> {
    override val defaultValue: AppPreferences = AppPreferences.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): AppPreferences =
        try {
            // readFrom is already called on the data store background thread
            AppPreferences.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }

    override suspend fun writeTo(t: AppPreferences, output: OutputStream) {
        // writeTo is already called on the data store background thread
        t.writeTo(output)
    }
}
