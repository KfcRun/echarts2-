/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.abel533.echarts.samples.fson.scatter;

import com.github.abel533.echarts.axis.TimeAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Orient;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.code.Y;
import com.github.abel533.echarts.series.Scatter;
import com.github.abel533.echarts.json.FsonOption;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author liuzh
 */
public class ScatterTest6 {

    @Test
    public void test() {
        //地址：http://echarts.baidu.com/doc/example/scatter6.html
        // echarts只能认识js的Date...
        FsonOption option = new FsonOption();
        option.title("时间坐标散点图","dataZoom支持");
        option.tooltip().trigger(Trigger.item).formatter("function (params) {" +
                "            return params.seriesName + ' （'  + '类目' + params.value[0] + '）<br/>'" +
                "                   + params.value[1] + ', ' " +
                "                   + params.value[2];" +
                "        }");
        option.legend("series1");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, Tool.restore, Tool.saveAsImage);
        option.dataZoom().show(true).start(30).end(70);
        option.dataRange().min(0).max(100).orient(Orient.horizontal).x(30).y(Y.center).color("lightgreen", "orange").splitNumber(5);
        option.grid().y2(80);
        option.xAxis(new TimeAxis().splitNumber(10));
        option.yAxis(new ValueAxis());
        option.animation(false);

        Scatter series1 = new Scatter("series1");
        series1.symbolSize("function (value){" +
                "                return Math.round(value[2]/10);" +
                "            }");
        series1.data(getData().toArray());

        option.series(series1);
        option.exportToHtml("scatter6.html");
        option.view();
    }

    public List<Object[]> getData(){
        List<Object[]> dataList = new ArrayList<Object[]>(1500);
        for (int i = 0; i < 1500; i++) {
            dataList.add(new Object[]{getDateStr(new Date(114,9,1,0,(int)Math.round(Math.random()*10000))),
                            (int)(round(Math.random()*30) - 0),
                            (int)(round(Math.random()*100) - 0)
            });
        }
        return dataList;
    }

    public String getDateStr(Date date){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public Double round(Double d) {
        BigDecimal bigDecimal = new BigDecimal(d.toString());
        bigDecimal = bigDecimal.round(new MathContext(2, RoundingMode.HALF_UP));
        return bigDecimal.doubleValue();
    }
}
