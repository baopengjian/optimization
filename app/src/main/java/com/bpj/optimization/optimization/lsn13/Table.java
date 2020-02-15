package com.bpj.optimization.optimization.lsn13;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Created by Administrator on 2016/11/2 0002.
 */

public class Table {
    protected int bb_pos;
    protected ByteBuffer bb;

    public Table() {
    }

    public ByteBuffer getByteBuffer() {
        return this.bb;
    }

    protected int __offset(int vtable_offset) {
        int vtable = this.bb_pos - this.bb.getInt(this.bb_pos);
        return vtable_offset < this.bb.getShort(vtable)?this.bb.getShort(vtable + vtable_offset):0;
    }

    protected int __indirect(int offset) {
        return offset + this.bb.getInt(offset);
    }

    protected String __string(int offset) {
        offset += this.bb.getInt(offset);
        if(this.bb.hasArray()) {
            return new String(this.bb.array(), this.bb.arrayOffset() + offset + 4, this.bb.getInt(offset), FlatBufferBuilder.utf8charset);
        } else {
            ByteBuffer bb = this.bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            byte[] copy = new byte[bb.getInt(offset)];
            bb.position(offset + 4);
            bb.get(copy);
            return new String(copy, 0, copy.length, FlatBufferBuilder.utf8charset);
        }
    }

    protected int __vector_len(int offset) {
        offset += this.bb_pos;
        offset += this.bb.getInt(offset);
        return this.bb.getInt(offset);
    }

    protected int __vector(int offset) {
        offset += this.bb_pos;
        return offset + this.bb.getInt(offset) + 4;
    }

    protected ByteBuffer __vector_as_bytebuffer(int vector_offset, int elem_size) {
        int o = this.__offset(vector_offset);
        if(o == 0) {
            return null;
        } else {
            ByteBuffer bb = this.bb.duplicate().order(ByteOrder.LITTLE_ENDIAN);
            int vectorstart = this.__vector(o);
            bb.position(vectorstart);
            bb.limit(vectorstart + this.__vector_len(o) * elem_size);
            return bb;
        }
    }

    protected Table __union(Table t, int offset) {
        offset += this.bb_pos;
        t.bb_pos = offset + this.bb.getInt(offset);
        t.bb = this.bb;
        return t;
    }

    protected static boolean __has_identifier(ByteBuffer bb, String ident) {
        if(ident.length() != 4) {
            throw new AssertionError("FlatBuffers: file identifier must be length 4");
        } else {
            for(int i = 0; i < 4; ++i) {
                if(ident.charAt(i) != (char)bb.get(bb.position() + 4 + i)) {
                    return false;
                }
            }

            return true;
        }
    }
}

