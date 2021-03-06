package com.bunker.bkframework.nio;

import static com.bunker.bkframework.newframework.Constants.*;

import java.nio.ByteBuffer;

import com.bunker.bkframework.newframework.Packet;

public class ByteBufferPacket implements Packet<ByteBuffer>{
	public boolean mIsFinal;
	public boolean mIsFrameworkPacket = false;
	public int mSequence;
	public int mPayloadSize;
	public ByteBuffer mPayloadBuffer;
	private short mFlag = 0;
	
	ByteBufferPacket() {
	}

	public ByteBufferPacket(int size) {
		mPayloadBuffer = ByteBuffer.allocate(size);
		mPayloadSize = size;
	}

	public ByteBufferPacket(ByteBuffer packet) {
		mFlag = packet.getShort(FLAG_OFFSET);
		parseFlag(mFlag);
		mPayloadSize = packet.getShort(PAYLOAD_SIZE_OFFSET);
		mSequence = packet.getInt(SEQUENCE_OFFSET);
		mPayloadBuffer = ByteBuffer.allocate(mPayloadSize);
		packet.position(PAYLOAD_OFFSET);
		packet.limit(PAYLOAD_OFFSET + mPayloadSize);
		mPayloadBuffer.put(packet);
		mPayloadBuffer.position(mPayloadSize);
	}

	void parseFlag(short flag) {
		if ((flag & FLAG_LAST) == FLAG_LAST)
			mIsFinal = true;
		if ((flag & FLAG_FRAMEWORK_PACKET) == FLAG_FRAMEWORK_PACKET)
			mIsFrameworkPacket = true;	
	}

	@Override
	public ByteBuffer getData() {
		ByteBuffer temp = mPayloadBuffer.duplicate();
		temp.flip();
		return temp;
	}

	@Override
	public int getSize() {
		return mPayloadSize;
	}

	@Override
	public boolean isFinal() {
		return mIsFinal;
	}

	@Override
	public int getSequence() {
		return mSequence;
	}

	@Override
	public void putDataAtLast(ByteBuffer packet) {
		mPayloadBuffer.put(packet);		
	}

	@Override
	public boolean isFramworkPacket() {
		return mIsFrameworkPacket;
	}

	@Override
	public void putFrameworkPacketFlag(boolean isFramework) {
		mIsFrameworkPacket = isFramework;
	}
}
