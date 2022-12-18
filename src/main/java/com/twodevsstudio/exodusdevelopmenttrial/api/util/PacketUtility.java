package com.twodevsstudio.exodusdevelopmenttrial.api.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class PacketUtility {
  public byte convertAngleToByte(float value) {
    return (byte) (value * 256.0F / 360.0F);
  }

  public float convertByteToAngle(byte value) {
    return (value * 360.0F) / 256.0F;
  }
}
