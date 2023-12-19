package br.edu.ufam.icomp.devtitans.qualityair.sensorview;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.edu.ufam.icomp.devtitans.qualityair.R;
import br.edu.ufam.icomp.devtitans.qualityair.sensors.SensorReadingStatus;

public class SensorsViewHolder extends RecyclerView.ViewHolder{
    private TextView tvName, tvDesc, tvValue;
    private ImageView ivStatus;
    public SensorsViewHolder(@NonNull View itemView) {
        super(itemView);
        tvName = itemView.findViewById(R.id.sensor_view_holder_tv_name);
        tvDesc = itemView.findViewById(R.id.sensor_view_holder_tv_desc);
        ivStatus = itemView.findViewById(R.id.sensor_view_holder_img_status);
        tvValue = itemView.findViewById(R.id.sensor_view_holder_value);
    }

    public void setName(String name){
        tvName.setText(name);
    }

    public void setDescription(String desc){
        tvDesc.setText(desc);
    }

    public void setSatus(SensorReadingStatus status){
        switch(status){
            case GOOD:{ivStatus.setImageResource(R.drawable.ball_green); break; }
            case MODERATE:{ivStatus.setImageResource(R.drawable.ball_yellow); break; }
            case BAD:{ivStatus.setImageResource(R.drawable.ball_orange); break; }
            case VERY_BAD:{ivStatus.setImageResource(R.drawable.ball_red); break; }
            case TERRIBLE:{ivStatus.setImageResource(R.drawable.ball_purple); break; }
            default: ivStatus.setImageResource(R.drawable.ball_nan);
        }
    }

    public void setValue(String value){
        tvValue.setText(value);
    }
}
