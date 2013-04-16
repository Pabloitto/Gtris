package com.gtris.models.ifaces;

import com.gtris.enums.ControlAlignment;
/**
 * Interface represent if a model can move
 * @author Pablo
 *
 */
public interface ICanMove {
	void move(ControlAlignment direction);
}
