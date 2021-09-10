package con.jwlee.itssum.ui.splash

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import con.jwlee.itssum.R
import con.jwlee.itssum.data.AppControl
import con.jwlee.itssum.ui.BaseFragment
import con.jwlee.itssum.util.Util
import kotlinx.android.synthetic.main.calc_act.*
import kotlinx.android.synthetic.main.main_toolbar.*
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager

class CalcFragment : BaseFragment(){

    var operator_plus: String = "+"
    var operator_minus: String = "-"
    var operator_multiply: String = "*"
    var operator_percent: String = "%"
    var operator_sum: String = "="

    var isNumber: Boolean = false
    var isFinish: Boolean = false

    lateinit var mContext : Context

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.calc_act, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() {
        mContext = this.requireContext()
        text_location.setText(AppControl.sName)
        text_per.setText(AppControl.sSale)
        header_title.setText(mContext.getString(R.string.title_calc))

        bt_back.setOnClickListener {
            when(AppControl.appIdx) {
                1 -> { findNavController().navigate(R.id.navigation_home) }
                3 -> { findNavController().navigate(R.id.navigation_menu) }
                else -> { findNavController().navigate(R.id.navigation_home) }
            }
        }

        calc_0.setOnClickListener(listenerNumber())
        calc_1.setOnClickListener(listenerNumber())
        calc_2.setOnClickListener(listenerNumber())
        calc_3.setOnClickListener(listenerNumber())
        calc_4.setOnClickListener(listenerNumber())
        calc_5.setOnClickListener(listenerNumber())
        calc_6.setOnClickListener(listenerNumber())
        calc_7.setOnClickListener(listenerNumber())
        calc_8.setOnClickListener(listenerNumber())
        calc_9.setOnClickListener(listenerNumber())

        calc_plus.setOnClickListener(listenerCalculator())
        calc_minus.setOnClickListener(listenerCalculator())
        calc_x.setOnClickListener(listenerCalculator())
        calc_per.setOnClickListener(listenerCalculator())
        calc_equal.setOnClickListener(listenerCalculator())
        calc_c.setOnClickListener(listenerCalculator())
        sale_add.setOnClickListener(listenerCalculator())
    }

    /*javascript lib 사용한 문자열 계산하기*/
    fun strResult(getstr: String): String {
        var manager: ScriptEngineManager = ScriptEngineManager()
        var engine: ScriptEngine = manager.getEngineByName("rhino")
        var obj: Any? = engine.eval(getstr)
        var result = obj.toString()
        result = result.substring(0, result.length-2);
        return result
    }

    fun listenerNumber(): View.OnClickListener {

        val listener = View.OnClickListener { view ->

            if(isFinish){
                edit_val.setText("")
                tv_history.setText("")
                isFinish=false
            }

            when (view.id) {
                R.id.calc_0 -> {
                    edit_val.append("0")
                    tv_history.append("0")
                }
                R.id.calc_1 -> {
                    edit_val.append("1")
                    tv_history.append("1")
                }
                R.id.calc_2 -> {
                    edit_val.append("2")
                    tv_history.append("2")
                }
                R.id.calc_3 -> {
                    edit_val.append("3")
                    tv_history.append("3")
                }
                R.id.calc_4 -> {
                    edit_val.append("4")
                    tv_history.append("4")
                }
                R.id.calc_5 -> {
                    edit_val.append("5")
                    tv_history.append("5")
                }
                R.id.calc_6 -> {
                    edit_val.append("6")
                    tv_history.append("6")
                }
                R.id.calc_7 -> {
                    edit_val.append("7")
                    tv_history.append("7")
                }
                R.id.calc_8 -> {
                    edit_val.append("8")
                    tv_history.append("8")
                }
                R.id.calc_9 -> {
                    edit_val.append("9")
                    tv_history.append("9")
                }
            }
            isNumber = true
        }
        return listener
    }

    /*계산 기호 전용 listener*/
    fun listenerCalculator(): View.OnClickListener {

        val listener = View.OnClickListener { view ->

            if(isFinish){
                edit_val.setText("")
                tv_history.setText("")
                isFinish=false
            }

            when (view.id) {
                R.id.calc_plus -> {
                    Result(operator_plus)
                }
                R.id.calc_minus -> {
                    Result(operator_minus)
                }
                R.id.calc_x -> {
                    Result(operator_multiply)
                }
                R.id.calc_per -> {
                    Result(operator_percent)
                }
                R.id.calc_equal -> {
                    Result(operator_sum)
                    isFinish=true
                }
                R.id.sale_add -> {
                    tv_history.append("/100" + "*" + salePercent())
                    Result(operator_sum)
                    isFinish=true
                }
                R.id.calc_c -> {
                    tv_history.setText("")
                    edit_val.setText("")
                }
            }
            isNumber = false
        }
        return listener
    }

    /*연속으로 연산자를 입력하는지 체크*/
    fun Result(str_operator: String) {
        //연속으로 연산자를 넣으면 안되기 때문에 반드시 숫자-연산자-숫자-연산자.. 순으로 입력받게 제어함
        if (isNumber) {
            if (str_operator == "=") {//계산할때에는 별도위 로직
                edit_val.setText("")
                if (edit_val.text.toString().length > 0) {
                    tv_history.append(edit_val.text.toString())
                }
                if (tv_history.text.toString().length > 0) {
                    edit_val.setText(strResult(tv_history.text.toString()))
                    Util().toastShort(mContext,tv_history.text.toString())
                }
            } else if(str_operator == "%") {
                edit_val.setText("")
                tv_history.append("/100" + "*")
            } else {
                tv_history.append(str_operator)
                Util().toastShort(mContext,mContext.getString(R.string.calc_plus_error))
                edit_val.setText("")
            }
        } else {

        }
    }

    fun salePercent() : Int {
        var per = 100
        when(AppControl.sLocation) {
            4 -> { per = (AppControl.yeonsuCashbag * 100).toInt() } // 연수구
            8 -> { per = (AppControl.westCashbag * 100).toInt() } // 서구
            else -> { per = (AppControl.incheonCashbag * 100).toInt() } // 기타
        }
        return per
    }
}