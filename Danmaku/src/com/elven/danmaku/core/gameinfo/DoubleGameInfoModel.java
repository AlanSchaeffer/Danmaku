package com.elven.danmaku.core.gameinfo;

public class DoubleGameInfoModel extends AbstractGameInfoModel<Double> {

	public DoubleGameInfoModel(double value) {
		super(value);
	}

	@Override
	public void increase(Double amount) {
		setValue(getValue() + amount);
	}

	@Override
	public void decrease(Double amount) {
		setValue(getValue() - amount);
	}
}
