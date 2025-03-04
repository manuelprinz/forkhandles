package dev.forkhandles.fabrikate

import java.io.File
import java.math.BigDecimal
import java.math.BigInteger
import java.net.URI
import java.net.URL
import java.time.Duration
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.OffsetDateTime
import java.time.OffsetTime
import java.time.YearMonth
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import java.util.Date
import java.util.UUID
import kotlin.random.Random
import kotlin.random.asJavaRandom

typealias Fabricator<T> = () -> T

class BooleanFabricator(private val random: Random = Random) : Fabricator<Boolean> {
    override fun invoke() = random.nextBoolean()
}

class LongFabricator(private val random: Random = Random) : Fabricator<Long> {
    override fun invoke() = random.nextLong()
}

class IntFabricator(private val random: Random = Random) : Fabricator<Int> {
    override fun invoke() = random.nextInt()
}

class DoubleFabricator(private val random: Random = Random) : Fabricator<Double> {
    override fun invoke() = random.nextDouble()
}

class FloatFabricator(private val random: Random = Random) : Fabricator<Float> {
    override fun invoke() = random.nextFloat()
}

class BigDecimalFabricator(
    private val size: Int = 10,
    private val random: Random = Random
) : Fabricator<BigDecimal> {
    override fun invoke() = BigInteger(size, random.asJavaRandom()).toBigDecimal()
}

class BigIntegerFabricator(
    private val size: Int = 10,
    private val random: Random = Random
) : Fabricator<BigInteger> {
    override fun invoke() = BigInteger(size, random.asJavaRandom())
}

class StringFabricator(
    private val length: IntRange = IntRange(1, 10),
    private val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9'),
    private val random: Random = Random,
) : Fabricator<String> {
    override fun invoke() = (1..random.nextInt(length.first, length.last + 1))
        .map { charPool.random(random) }
        .joinToString("")
}

class CharFabricator(
    private val charPool: CharRange = ('A'..'z'),
    private val random: Random = Random
) : Fabricator<Char> {
    override fun invoke(): Char = charPool.random(random)
}

class ByteFabricator(
    private val random: Random = Random
) : Fabricator<Byte> {
    override fun invoke(): Byte = random.nextBytes(1).first()
}

class BytesFabricator(
    private val size: Int = 10,
    private val random: Random = Random,
) : Fabricator<ByteArray> {
    override fun invoke() = random.nextBytes(size)
}

class InstantFabricator(private val random: Random = Random) : Fabricator<Instant> {
    override fun invoke(): Instant = Instant.ofEpochSecond(random.nextLong(0, 1735689600))
}

class LocalDateFabricator(private val random: Random = Random) : Fabricator<LocalDate> {
    override fun invoke(): LocalDate = LocalDate.ofInstant(InstantFabricator(random)(), UTC)
}

class LocalTimeFabricator(private val random: Random = Random) : Fabricator<LocalTime> {
    override fun invoke(): LocalTime = LocalTime.ofInstant(InstantFabricator(random)(), UTC)
}

class LocalDateTimeFabricator(private val random: Random = Random) : Fabricator<LocalDateTime> {
    override fun invoke(): LocalDateTime = LocalDateTime.ofInstant(InstantFabricator(random)(), UTC)
}

class YearMonthFabricator(private val random: Random = Random) : Fabricator<YearMonth> {
    override fun invoke(): YearMonth = YearMonth.of(random.nextInt(1970, 2030), random.nextInt(1, 12))
}

class OffsetDateTimeFabricator(private val random: Random = Random) : Fabricator<OffsetDateTime> {
    override fun invoke(): OffsetDateTime = OffsetDateTime.ofInstant(InstantFabricator(random)(), UTC)
}

class OffsetTimeFabricator(private val random: Random = Random) : Fabricator<OffsetTime> {
    override fun invoke(): OffsetTime = OffsetTime.ofInstant(InstantFabricator(random)(), UTC)
}

class ZonedDateTimeFabricator(private val random: Random = Random) : Fabricator<ZonedDateTime> {
    override fun invoke(): ZonedDateTime = ZonedDateTime.ofInstant(InstantFabricator(random)(), UTC)
}

class DateFabricator(private val random: Random = Random) : Fabricator<Date> {
    override fun invoke(): Date = Date.from(InstantFabricator(random)())
}

class DurationFabricator(private val random: Random = Random) : Fabricator<Duration> {
    override fun invoke(): Duration = Duration.ofDays(random.nextLong(1, 10))
}

class UUIDFabricator(private val random: Random = Random) : Fabricator<UUID> {
    override fun invoke(): UUID = UUID(random.nextLong(), random.nextLong())
}

class UriFabricator(private val random: Random = Random) : Fabricator<URI> {
    override fun invoke(): URI = URI.create("https://${StringFabricator(random = random)()}.com")
}

class UrlFabricator(private val random: Random = Random) : Fabricator<URL> {
    override fun invoke(): URL = URL("https://${StringFabricator(random = random)().filter { it.isLetterOrDigit() }}.com")
}

class FileFabricator : Fabricator<File> {
    override fun invoke(): File = File.createTempFile("fabrikate", null).apply { deleteOnExit() }
}

class AnyFabricator(private val any: Any = "anything") : Fabricator<Any> {
    override fun invoke(): Any = any
}
