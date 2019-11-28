package com.wjp.justforfun

import org.junit.Test

import org.junit.Assert.*
import java.text.SimpleDateFormat
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.logging.SimpleFormatter

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun replace() {
        val source="\"<!--IMG#0--><!--IMG#1--><p>　　事故桥下锡港路已恢复通行；今后无锡境内货车上高速前要先称重；设计单位称桥梁设计符合规范</p><p>　　经过一天多的施工救援，昨日早晨，无锡侧翻高架桥下方的锡港路恢复通行。无锡市政府发布消息称，今后无锡境内货车上高速前要称重，载重合格才能通过卡口。同时，桥梁设计单位表示，确认设计符合各项规范要求。此外，记者注意到，事故发生后，当地货运市场依旧繁忙，但是货车一次性拉货的量大幅减少，致运费上涨。</p><p>　　新京报讯10月10日傍晚，江苏无锡境内312国道、锡港路上跨桥发生桥面侧翻。昨日早晨，事故高架桥下方的锡港路恢复通行。无锡市政府发布消息称，今后无锡境内货车需称重上高速。</p><p>　　<b>事故调查已启动，5名专家加入调查组</b></p><p>　　昨日，新京报记者在现场看到，原横跨在锡港路的侧翻桥面目前已清理完毕，施工车辆基本撤出公路，锡港路恢复通行，附近312国道仍双向管制。</p><p>　　无锡市公安局交警支队发布消息称，12日7时10分，锡港路南北方向开通，交通恢复正常。312国道无锡段目前仍然有临时交通管制。</p><p>　　新京报记者从无锡市人民政府新闻办获悉，事故发生后，无锡市迅速成立交通事故调查组，下设综合组、管理组和技术组，并邀请5名专家，全面开展事故调查。</p><p>　　10月11日，事故调查工作正式启动。调查组对车主、肇事车辆、载货情况、桥面倾覆被压车辆、桥面上侧翻车辆，以及运输公司、货物装载码头单位等展开先期调查，并对肇事驾驶员、车主、运输企业法人代表以及货物装载码头主要负责人和管理人员等依法采取强制措施。</p><p>　　调查组聘请的专家组调取了事故桥梁及相邻两联桥梁的9大类相关资料，并进行技术层面分析，待综合各方相关资料后，专家组将对事故原因作出进一步分析判断，最终形成技术分析报告。</p><p>　　与此同时，调查组正在收集与事故有关的法律法规和资料，根据技术原因分析调查的进展，对与事故有关的管理层面的原因和责任作深入调查。</p><p>　　<b>无锡境内货车需称重上高速</b></p><p>　　昨日，无锡市政府发布消息称，今后无锡境内货车上高速前，先上“拒超秤台”检测，载重合格才能通过卡口。</p><p>　　截至目前，包括无锡东、无锡西、无锡北、玉祁、南泉等在内的无锡所有高速公路收费站入口“拒超称重检测系统设施”全部安装结束，并已具备使用条件。</p><p>　　据无锡高速公路管理部门相关负责人介绍，正常范围内的载货量在自动扣费后自动放行，如果超限，车辆将被禁止进入高速并强制作卸货处理。</p><p>　　来自江苏省交通运输综合行政执法局的消息，江苏将在年底前，全面完成全省高速公路收费站入口称重检测系统建设任务。</p><p>　　<b>设计单位称设计符合规范要求</b></p><p>"
        source.replace("<!--IMG#1--><p","hello").also {
            println(it)
        }
    }


}