// automatically generated, do not modify

package com.bpj.optimization.optimization.lsn13;

import java.nio.*;
import java.lang.*;
import java.util.*;

public class Items extends Table {
  public static Items getRootAsItems(ByteBuffer _bb) { return getRootAsItems(_bb, new Items()); }
  public static Items getRootAsItems(ByteBuffer _bb, Items obj) { _bb.order(ByteOrder.LITTLE_ENDIAN); return (obj.__init(_bb.getInt(_bb.position()) + _bb.position(), _bb)); }
  public Items __init(int _i, ByteBuffer _bb) { bb_pos = _i; bb = _bb; return this; }

  public long ItemId() { int o = __offset(4); return o != 0 ? bb.getLong(o + bb_pos) : 0; }
  public int timestemp() { int o = __offset(6); return o != 0 ? bb.getInt(o + bb_pos) : 0; }
  public Basic basic(int j) { return basic(new Basic(), j); }
  public Basic basic(Basic obj, int j) { int o = __offset(8); return o != 0 ? obj.__init(__indirect(__vector(o) + j * 4), bb) : null; }
  public int basicLength() { int o = __offset(8); return o != 0 ? __vector_len(o) : 0; }

  public static int createItems(FlatBufferBuilder builder,
      long ItemId,
      int timestemp,
      int basic) {
    builder.startObject(3);
    Items.addItemId(builder, ItemId);
    Items.addBasic(builder, basic);
    Items.addTimestemp(builder, timestemp);
    return Items.endItems(builder);
  }

  public static void startItems(FlatBufferBuilder builder) { builder.startObject(3); }
  public static void addItemId(FlatBufferBuilder builder, long ItemId) { builder.addLong(0, ItemId, 0); }
  public static void addTimestemp(FlatBufferBuilder builder, int timestemp) { builder.addInt(1, timestemp, 0); }
  public static void addBasic(FlatBufferBuilder builder, int basicOffset) { builder.addOffset(2, basicOffset, 0); }
  public static int createBasicVector(FlatBufferBuilder builder, int[] data) { builder.startVector(4, data.length, 4); for (int i = data.length - 1; i >= 0; i--) builder.addOffset(data[i]); return builder.endVector(); }
  public static void startBasicVector(FlatBufferBuilder builder, int numElems) { builder.startVector(4, numElems, 4); }
  public static int endItems(FlatBufferBuilder builder) {
    int o = builder.endObject();
    return o;
  }
  public static void finishItemsBuffer(FlatBufferBuilder builder, int offset) { builder.finish(offset); }
};

