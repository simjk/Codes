package e.j_enn.repa.boundary;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import e.j_enn.repa.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CalculatorFragment extends Fragment {

    double amt;
    double rate;
    int term;
    int cTerm;
    double yRate;
    double total = 0;
    double principlePayment;
    double interestPayment;

    Toolbar toolbar;
    EditText loanAmt;
    EditText interestRate;
    EditText loanTerm;
    Button submitBtn;

    public CalculatorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_calculator, container, false);
    }

    public void initView() {
        loanAmt = (EditText) getActivity().findViewById(R.id.loanAmt);
        interestRate = (EditText) getActivity().findViewById(R.id.interestRate);
        loanTerm = (EditText) getActivity().findViewById(R.id.loanTerm);
        //   output = (TextView) getActivity().findViewById(R.id.output);
        //Button submitBtn = (Button) getActivity().findViewById(R.id.submitBtn);
        //submitBtn.setOnClickListener(new View.OnClickListener()
        getActivity().findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NumberValidation() == true) {
                    donutValue();
                }
            }
        });
    }

    public void donutValue() {
        amt = Double.valueOf(loanAmt.getText().toString());
        yRate = Double.valueOf(interestRate.getText().toString());
        cTerm = Integer.valueOf(loanTerm.getText().toString());
        rate = (yRate / 100) / 12;
        term = cTerm * 12;


        total = amt * (rate * Math.pow((rate + 1), term)) / (Math.pow(rate + 1, term) - 1);
        interestPayment = (int) amt / 1000;
        principlePayment = (int) total - interestPayment;
        //interestPayment = Integer.parseInt(Double.toString(amt/1000));
//
//                double numerator = rate*Math.pow(1.00+rate,term);
//                double denominator = Math.pow(1.00+rate, term - 1.00);
//                double total = amt*(numerator/denominator);
        //    output.setText(Double.toString(principlePayment));
        DonutChart(total, interestPayment, principlePayment);
    }

    public boolean NumberValidation() {
        boolean result = true;
        if (loanAmt.getText().toString().isEmpty()) {
            loanAmt.requestFocus();
            loanAmt.setError("Loan Amount cannot be empty.");
            result = false;
        } else if (interestRate.getText().toString().isEmpty()) {
            interestRate.requestFocus();
            interestRate.setError("Interest rate cannot be empty.");
            result = false;
        } else if (loanTerm.getText().toString().isEmpty()) {
            loanTerm.requestFocus();
            loanTerm.setError("Loan Term cannot be empty.");
            result = false;
        } else {
            amt = Double.valueOf(loanAmt.getText().toString());
            yRate = Double.valueOf(interestRate.getText().toString());
            cTerm = Integer.valueOf(loanTerm.getText().toString());

            if (amt >= 100000 && yRate >= 0.1 && yRate <= 7 && cTerm >= 5 && cTerm <= 40) {
                result = true;
            } else {

                if (amt < 100000) {
                    loanAmt.requestFocus();
                    loanAmt.setError("The amount is less than S$100,000.");
                    result = false;
                }

                if (yRate >= 0.1 && yRate <= 7) {
                    result = true;
                } else {
                    interestRate.requestFocus();
                    interestRate.setError("Interest Rate must be in between 0.1% ~ 7.");
                    result = false;
                }

                if (cTerm >= 5 && cTerm <= 40) {
                    result = true;
                } else {
                    loanTerm.requestFocus();
                    loanTerm.setError("Loan Term cannot be less than 5 years or more than 40 years.");
                    result = false;
                }
            }
        }
        return result;
    }


    public void onViewCreated(View view, Bundle savedInstanceState) {
        toolbar = ((Toolbar) getActivity().findViewById(R.id.toolbar));
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        TextView tv = ((TextView) getView().findViewById(R.id.toolbar_title));
        tv.setText("Calculator");

        double loanAmt1 = 1000000;
        double interestRate1 = 1.2;
        int loanterm1 = 30;

        rate = (interestRate1 / 100) / 12;
        term = loanterm1 * 12;

        total = loanAmt1 * (rate * Math.pow((rate + 1), term)) / (Math.pow(rate + 1, term) - 1);
        interestPayment = (int) loanAmt1 / 1000;
        principlePayment = (int) total - interestPayment;

        DonutChart(total, interestPayment, principlePayment);

        initView();
    }

    private void DonutChart(double total, double interestPayment, double principlePayment) {
        List<PieEntry> mPayment = new ArrayList<>();
        mPayment.add(new PieEntry((float) interestPayment, "$" + (int) interestPayment + " Starting interest payment per month"));
        mPayment.add(new PieEntry((float) principlePayment, "$" + (int) principlePayment + " Starting principle payment per month"));

        int[] colors = {Color.rgb(253, 84, 101), Color.rgb(110, 110, 110)};
        PieDataSet donut = new PieDataSet(mPayment, "");
        donut.setColors(colors);
        PieData data = new PieData(donut);
        data.setDrawValues(false);


        PieChart chart = (PieChart) getActivity().findViewById(R.id.chart);

        Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_CENTER);
        l.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);
        l.setWordWrapEnabled(true);
        l.setTextSize(14);

        chart.setHoleRadius(70f);
        chart.setCenterText("$" + (int) total + "\nper month");
        chart.setCenterTextSize(15);
        chart.animateX(1000);
        chart.setTransparentCircleAlpha(0);
        chart.setData(data);
        chart.invalidate();
        chart.getDescription().setEnabled(false);
        chart.setDrawSliceText(false);


    }

}
