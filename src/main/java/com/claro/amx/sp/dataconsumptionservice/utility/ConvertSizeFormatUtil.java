package com.claro.amx.sp.dataconsumptionservice.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ConvertSizeFormatUtil {

    public static BigDecimal convertSize(BigDecimal size, String unitName) {
        final List<SizeUnitPrefixes> units = SizeUnitPrefixes.unitsInDescending();
        if (size.compareTo(BigDecimal.ZERO) < 0)
            throw new IllegalArgumentException("Invalid file size: " + size);
        BigDecimal result = null;
        for (SizeUnitPrefixes unit : units) {
            if (size.compareTo(BigDecimal.ZERO) > 0 && unit.name().equalsIgnoreCase(unitName)) {
                result = size.divide(new BigDecimal(unit.getUnitBase()), 2, RoundingMode.HALF_EVEN);
                break;
            }
        }
        return result == null ? size.divide(new BigDecimal(SizeUnitPrefixes.BYTES.getUnitBase()), 2, RoundingMode.HALF_EVEN) : result;
    }

    enum SizeUnitPrefixes {
        BYTES(1L),
        KB(BYTES.unitBase * 1024),
        MB(KB.unitBase * 1024),
        GB(MB.unitBase * 1024),
        TB(GB.unitBase * 1024),
        PB(TB.unitBase * 1024),
        EB(PB.unitBase * 1024);

        private final Long unitBase;

        public static List<SizeUnitPrefixes> unitsInDescending() {
            List<SizeUnitPrefixes> list = Arrays.asList(values());
            Collections.reverse(list);
            return list;
        }

        public Long getUnitBase() {
            return unitBase;
        }

        SizeUnitPrefixes(long unitBase) {
            this.unitBase = unitBase;
        }
    }
}
