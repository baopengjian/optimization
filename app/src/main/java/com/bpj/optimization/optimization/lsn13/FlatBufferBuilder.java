package com.bpj.optimization.optimization.lsn13;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class FlatBufferBuilder {
    ByteBuffer bb;
    int space;
    static final Charset utf8charset = Charset.forName("UTF-8");
    int minalign;
    int[] vtable;
    int vtable_in_use;
    boolean nested;
    boolean finished;
    int object_start;
    int[] vtables;
    int num_vtables;
    int vector_num_elems;
    boolean force_defaults;

    public FlatBufferBuilder(int initial_size) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        if(initial_size <= 0) {
            initial_size = 1;
        }

        this.space = initial_size;
        this.bb = newByteBuffer(initial_size);
    }

    public FlatBufferBuilder() {
        this(1024);
    }

    public FlatBufferBuilder(ByteBuffer existing_bb) {
        this.minalign = 1;
        this.vtable = null;
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.vtables = new int[16];
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        this.force_defaults = false;
        this.init(existing_bb);
    }

    public FlatBufferBuilder init(ByteBuffer existing_bb) {
        this.bb = existing_bb;
        this.bb.clear();
        this.bb.order(ByteOrder.LITTLE_ENDIAN);
        this.minalign = 1;
        this.space = this.bb.capacity();
        this.vtable_in_use = 0;
        this.nested = false;
        this.finished = false;
        this.object_start = 0;
        this.num_vtables = 0;
        this.vector_num_elems = 0;
        return this;
    }

    static ByteBuffer newByteBuffer(int capacity) {
        ByteBuffer newbb = ByteBuffer.allocate(capacity);
        newbb.order(ByteOrder.LITTLE_ENDIAN);
        return newbb;
    }

    static ByteBuffer growByteBuffer(ByteBuffer bb) {
        int old_buf_size = bb.capacity();
        if((old_buf_size & -1073741824) != 0) {
            throw new AssertionError("FlatBuffers: cannot grow buffer beyond 2 gigabytes.");
        } else {
            int new_buf_size = old_buf_size << 1;
            bb.position(0);
            ByteBuffer nbb = newByteBuffer(new_buf_size);
            nbb.position(new_buf_size - old_buf_size);
            nbb.put(bb);
            return nbb;
        }
    }

    public int offset() {
        return this.bb.capacity() - this.space;
    }

    public void pad(int byte_size) {
        for(int i = 0; i < byte_size; ++i) {
            this.bb.put(--this.space, (byte)0);
        }

    }

    public void prep(int size, int additional_bytes) {
        if(size > this.minalign) {
            this.minalign = size;
        }

        int align_size;
        int old_buf_size;
        for(align_size = ~(this.bb.capacity() - this.space + additional_bytes) + 1 & size - 1; this.space < align_size + size + additional_bytes; this.space += this.bb.capacity() - old_buf_size) {
            old_buf_size = this.bb.capacity();
            this.bb = growByteBuffer(this.bb);
        }

        this.pad(align_size);
    }

    public void putBoolean(boolean x) {
        this.bb.put(--this.space, (byte)(x?1:0));
    }

    public void putByte(byte x) {
        this.bb.put(--this.space, x);
    }

    public void putShort(short x) {
        this.bb.putShort(this.space -= 2, x);
    }

    public void putInt(int x) {
        this.bb.putInt(this.space -= 4, x);
    }

    public void putLong(long x) {
        this.bb.putLong(this.space -= 8, x);
    }

    public void putFloat(float x) {
        this.bb.putFloat(this.space -= 4, x);
    }

    public void putDouble(double x) {
        this.bb.putDouble(this.space -= 8, x);
    }

    public void addBoolean(boolean x) {
        this.prep(1, 0);
        this.putBoolean(x);
    }

    public void addByte(byte x) {
        this.prep(1, 0);
        this.putByte(x);
    }

    public void addShort(short x) {
        this.prep(2, 0);
        this.putShort(x);
    }

    public void addInt(int x) {
        this.prep(4, 0);
        this.putInt(x);
    }

    public void addLong(long x) {
        this.prep(8, 0);
        this.putLong(x);
    }

    public void addFloat(float x) {
        this.prep(4, 0);
        this.putFloat(x);
    }

    public void addDouble(double x) {
        this.prep(8, 0);
        this.putDouble(x);
    }

    public void addOffset(int off) {
        this.prep(4, 0);

        assert off <= this.offset();

        off = this.offset() - off + 4;
        this.putInt(off);
    }

    public void startVector(int elem_size, int num_elems, int alignment) {
        this.notNested();
        this.vector_num_elems = num_elems;
        this.prep(4, elem_size * num_elems);
        this.prep(alignment, elem_size * num_elems);
        this.nested = true;
    }

    public int endVector() {
        if(!this.nested) {
            throw new AssertionError("FlatBuffers: endVector called without startVector");
        } else {
            this.nested = false;
            this.putInt(this.vector_num_elems);
            return this.offset();
        }
    }

    public int createString(String s) {
        byte[] utf8 = s.getBytes(utf8charset);
        this.addByte((byte)0);
        this.startVector(1, utf8.length, 1);
        this.bb.position(this.space -= utf8.length);
        this.bb.put(utf8, 0, utf8.length);
        return this.endVector();
    }

    public int createString(ByteBuffer s) {
        int length = s.remaining();
        this.addByte((byte)0);
        this.startVector(1, length, 1);
        this.bb.position(this.space -= length);
        this.bb.put(s);
        return this.endVector();
    }

    public void finished() {
        if(!this.finished) {
            throw new AssertionError("FlatBuffers: you can only access the serialized buffer after it has been finished by FlatBufferBuilder.finish().");
        }
    }

    public void notNested() {
        if(this.nested) {
            throw new AssertionError("FlatBuffers: object serialization must not be nested.");
        }
    }

    public void Nested(int obj) {
        if(obj != this.offset()) {
            throw new AssertionError("FlatBuffers: struct must be serialized inline.");
        }
    }

    public void startObject(int numfields) {
        this.notNested();
        if(this.vtable == null || this.vtable.length < numfields) {
            this.vtable = new int[numfields];
        }

        this.vtable_in_use = numfields;
        Arrays.fill(this.vtable, 0, this.vtable_in_use, 0);
        this.nested = true;
        this.object_start = this.offset();
    }

    public void addBoolean(int o, boolean x, boolean d) {
        if(this.force_defaults || x != d) {
            this.addBoolean(x);
            this.slot(o);
        }

    }

    public void addByte(int o, byte x, int d) {
        if(this.force_defaults || x != d) {
            this.addByte(x);
            this.slot(o);
        }

    }

    public void addShort(int o, short x, int d) {
        if(this.force_defaults || x != d) {
            this.addShort(x);
            this.slot(o);
        }

    }

    public void addInt(int o, int x, int d) {
        if(this.force_defaults || x != d) {
            this.addInt(x);
            this.slot(o);
        }

    }

    public void addLong(int o, long x, long d) {
        if(this.force_defaults || x != d) {
            this.addLong(x);
            this.slot(o);
        }

    }

    public void addFloat(int o, float x, double d) {
        if(this.force_defaults || (double)x != d) {
            this.addFloat(x);
            this.slot(o);
        }

    }

    public void addDouble(int o, double x, double d) {
        if(this.force_defaults || x != d) {
            this.addDouble(x);
            this.slot(o);
        }

    }

    public void addOffset(int o, int x, int d) {
        if(this.force_defaults || x != d) {
            this.addOffset(x);
            this.slot(o);
        }

    }

    public void addStruct(int voffset, int x, int d) {
        if(x != d) {
            this.Nested(x);
            this.slot(voffset);
        }

    }

    public void slot(int voffset) {
        this.vtable[voffset] = this.offset();
    }

    public int endObject() {
        if(this.vtable != null && this.nested) {
            this.addInt(0);
            int vtableloc = this.offset();

            for(int standard_fields = this.vtable_in_use - 1; standard_fields >= 0; --standard_fields) {
                short existing_vtable = (short)(this.vtable[standard_fields] != 0?vtableloc - this.vtable[standard_fields]:0);
                this.addShort(existing_vtable);
            }

            boolean var9 = true;
            this.addShort((short)(vtableloc - this.object_start));
            this.addShort((short)((this.vtable_in_use + 2) * 2));
            int var10 = 0;

            label47:
            for(int i = 0; i < this.num_vtables; ++i) {
                int vt1 = this.bb.capacity() - this.vtables[i];
                int vt2 = this.space;
                short len = this.bb.getShort(vt1);
                if(len == this.bb.getShort(vt2)) {
                    for(int j = 2; j < len; j += 2) {
                        if(this.bb.getShort(vt1 + j) != this.bb.getShort(vt2 + j)) {
                            continue label47;
                        }
                    }

                    var10 = this.vtables[i];
                    break;
                }
            }

            if(var10 != 0) {
                this.space = this.bb.capacity() - vtableloc;
                this.bb.putInt(this.space, var10 - vtableloc);
            } else {
                if(this.num_vtables == this.vtables.length) {
                    this.vtables = Arrays.copyOf(this.vtables, this.num_vtables * 2);
                }

                this.vtables[this.num_vtables++] = this.offset();
                this.bb.putInt(this.bb.capacity() - vtableloc, this.offset() - vtableloc);
            }

            this.nested = false;
            return vtableloc;
        } else {
            throw new AssertionError("FlatBuffers: endObject called without startObject");
        }
    }

    public void required(int table, int field) {
        int table_start = this.bb.capacity() - table;
        int vtable_start = table_start - this.bb.getInt(table_start);
        boolean ok = this.bb.getShort(vtable_start + field) != 0;
        if(!ok) {
            throw new AssertionError("FlatBuffers: field " + field + " must be set");
        }
    }

    public void finish(int root_table) {
        this.prep(this.minalign, 4);
        this.addOffset(root_table);
        this.bb.position(this.space);
        this.finished = true;
    }

    public void finish(int root_table, String file_identifier) {
        this.prep(this.minalign, 8);
        if(file_identifier.length() != 4) {
            throw new AssertionError("FlatBuffers: file identifier must be length 4");
        } else {
            for(int i = 3; i >= 0; --i) {
                this.addByte((byte)file_identifier.charAt(i));
            }

            this.finish(root_table);
        }
    }

    public FlatBufferBuilder forceDefaults(boolean forceDefaults) {
        this.force_defaults = forceDefaults;
        return this;
    }

    public ByteBuffer dataBuffer() {
        this.finished();
        return this.bb;
    }

    /** @deprecated */
    @Deprecated
    private int dataStart() {
        this.finished();
        return this.space;
    }

    public byte[] sizedByteArray(int start, int length) {
        this.finished();
        byte[] array = new byte[length];
        this.bb.position(start);
        this.bb.get(array);
        return array;
    }

    public byte[] sizedByteArray() {
        return this.sizedByteArray(this.space, this.bb.capacity() - this.space);
    }
}
