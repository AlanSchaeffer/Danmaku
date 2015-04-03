package com.elven.danmaku.core.gameinfo;


public class LongGameInfoModel extends AbstractGameInfoModel<Long> {

	public LongGameInfoModel(long value) {
		super(value);
	}
	
	@Override
	public void increase(Long amount) {
		setValue(getValue() + amount);
	}

	@Override
	public void decrease(Long amount) {
		setValue(getValue() - amount);
	}
}
