package viit.com.hackathon_2k19.Modal_class;

public class Fetch_final_output {
    public Double comp_total_discharge,total_discharge,velo_mean,avg_velocity,max_velocity;


    public Fetch_final_output() {
    }

    public Fetch_final_output(Double comp_total_discharge, Double total_discharge, Double velo_mean, Double avg_velocity, Double max_velocity) {
        this.comp_total_discharge = comp_total_discharge;
        this.total_discharge = total_discharge;
        this.velo_mean = velo_mean;
        this.avg_velocity = avg_velocity;
        this.max_velocity = max_velocity;
    }

    public Double getComp_total_discharge() {
        return comp_total_discharge;
    }

    public void setComp_total_discharge(Double comp_total_discharge) {
        this.comp_total_discharge = comp_total_discharge;
    }

    public Double getTotal_discharge() {
        return total_discharge;
    }

    public void setTotal_discharge(Double total_discharge) {
        this.total_discharge = total_discharge;
    }

    public Double getVelo_mean() {
        return velo_mean;
    }

    public void setVelo_mean(Double velo_mean) {
        this.velo_mean = velo_mean;
    }

    public Double getAvg_velocity() {
        return avg_velocity;
    }

    public void setAvg_velocity(Double avg_velocity) {
        this.avg_velocity = avg_velocity;
    }

    public Double getMax_velocity() {
        return max_velocity;
    }

    public void setMax_velocity(Double max_velocity) {
        this.max_velocity = max_velocity;
    }
}
