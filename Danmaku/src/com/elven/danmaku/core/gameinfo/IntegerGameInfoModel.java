package com.elven.danmaku.core.gameinfo;


public class IntegerGameInfoModel extends AbstractGameInfoModel<Integer> {

	public IntegerGameInfoModel(int value) {
		super(value);
	}

	@Override
	public void increase(Integer amount) {
		setValue(getValue() + amount);
	}

	@Override
	public void decrease(Integer amount) {
		setValue(getValue() - amount);
	}
}
