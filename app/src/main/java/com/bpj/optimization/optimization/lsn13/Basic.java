// automatically generated, do not modify

package com.bpj.optimization.optimization.lsn13;

import java.nio.*;
import java.lang.*;
import java.util.*;

public class Basic extends Table {
  public static Basic getRootAsBasic(ByteBuffer _bb) { return getRootAsBasic(_bb, new Basic()); }
  public static Basic getRootAsBasic(ByteBuffer _bb, Basic obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Basic __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public int id() { int o = __offset(4); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public String name() { int o = __offset(6); return o != 0 ? __string(o + bb_pos) : null; }
  public ByteBuffer nameAsByteBuffer() { return __vector_as_bytebuffer(6, 1); }
  public int email() { int o = __offset(8); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public long code() { int o = __offset(10); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public boolean isVip() { int o = __offset(12); return o != 0 ? 0!=bb.get(o + bb_pos) : false; }
  public int count() { int o = __offset(14); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public Car carList(int j) { return carList(new Car(), j); }
  public Car carList(Car obj, int j) { int o = __offset(16); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int carListLength() { int o = __offset(16); return o != 0 ? __vector_len(o) : 0; }

  public static int createBasic(FlatBufferBuilder builder,
      int id,
      int name,
      int email,
      long code,
      boolean isVip,
      int count,
      int carList) {
    builder.startObject(7);
    Basic.addCode(builder, code);
    Basic.addCarList(builder, carList);
    Basic.addCount(builder, count);
    Basic.addEmail(builder, email);
    Basic.addName(builder, name);
    Basic.addId(builder, id);
    Basic.addIsVip(builder, isVip);
    return Basic.endBasic(builder);
  }

  public static void startBasic(FlatBufferBuilder builder) { builder.startObject(7); }
  public static void addId(FlatBufferBuilder builder, int id) { builder.addInt(0, id, 0); }
  public static void addName(FlatBufferBuilder builder, int nameOffset) { builder.addOffset(1, nameOffset, 0); }
  public static void addEmail(FlatBufferBuilder builder, int email) { builder.addInt(2, email, 0); }
  public static void addCode(FlatBufferBuilder builder, long code) { builder.addLong(3, code, 0); }
  public static void addIsVip(FlatBufferBuilder builder, boolean isVip) { builder.addBoolean(4, isVip, false); }
  public static void addCount(FlatBufferBuilder builder, int count) { builder.addInt(5, count, 0); }
  public static void addCarList(FlatBufferBuilder builder, int carListOffset) { builder.addOffset(6, carListOffset, 0); }
  public static int createCarListVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startCarListVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endBasic(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
};

