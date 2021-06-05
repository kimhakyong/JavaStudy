package refactoring.java.r03_ia;

import java.util.Arrays;

public class SortSample {
    private final int[] _data;

    public SortSample(int[] data) {
        _data = new int[data.length];
        System.arraycopy(data, 0, _data, 0, data.length);
    }

    public void sort() {
        for (int x = 0; x < _data.length - 1; x++) {
            int m = x;
            for (int y = x + 1; y < _data.length; y++) {
                if (_data[m] > _data[y]) {
                    m = y;
                }
            }
            // 주석~~~
            assert isMin(m, x, _data.length - 1) : this + ", m = " + m + ", x = " + x;
            int v = _data[m];
            _data[m] = _data[x];
            _data[x] = v;
            assert isSorted(x + 1) : this + ", x = " + x;
            // 주석~~~
        }
    }

    public String toString() {
        StringBuilder buffer = new StringBuilder();
        buffer.append("[ ");

//        for (int datum : _data) {
//            buffer.append(datum).append(", ");
//        }
        Arrays.stream(_data).forEach(v -> buffer.append(v).append(", "));
        buffer.append("]");

        return buffer.toString();
    }

    private boolean isMin(int pos, int start, int end) {
//        return Arrays.stream(_data, start, end + 1).allMatch(v -> v >= _data[pos]);
        return Arrays.stream(_data, start, end + 1).min().orElse(Integer.MIN_VALUE) == _data[pos];
    }

    private boolean isSorted(int end) {
        for (int i = 0; i < end; i++) {
            if (_data[i] > _data[i + 1]) {
                return false;
            }
        }

        return true;
    }
}
