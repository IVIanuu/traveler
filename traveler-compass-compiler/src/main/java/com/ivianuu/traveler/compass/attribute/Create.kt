package com.ivianuu.traveler.compass.attribute

import com.ivianuu.traveler.compass.attribute.array.*
import com.ivianuu.traveler.compass.attribute.boxedprimitives.*
import com.ivianuu.traveler.compass.attribute.list.*

fun attributeSerializers(): List<AttributeSerializeLogic> = listOf(
    PrimitiveAttributeSerializeLogic(),
    ParcelableAttributeSerializeLogic(),
    StringAttributeSerializeLogic(),
    IntListAttributeSerializeLogic(),
    FloatListAttributeSerializeLogic(),
    DoubleListAttributeSerializeLogic(),
    CharListAttributeSerializeLogic(),
    BooleanListAttributeSerializeLogic(),
    LongListAttributeSerializeLogic(),
    ShortListAttributeSerializeLogic(),
    IntArrayAttributeSerializeLogic(),
    ShortArrayAttributeSerializeLogic(),
    FloatArrayAttributeSerializeLogic(),
    DoubleArrayAttributeSerializeLogic(),
    LongArrayAttributeSerializeLogic(),
    BooleanArrayAttributeSerializeLogic(),
    CharArrayAttributeSerializeLogic(),
    ByteArrayAttributeSerializeLogic(),
    ParcelableArrayAttributeSerializeLogic(),
    StringArrayAttributeSerializeLogic(),
    StringListAttributeSerializeLogic(),
    ParcelableListAttributeSerializeLogic(),
    BoxedIntegerAttributeSerializeLogic(),
    BoxedFloatAttributeSerializeLogic(),
    BoxedDoubleAttributeSerializeLogic(),
    BoxedShortAttributeSerializeLogic(),
    BoxedBooleanAttributeSerializeLogic()
)