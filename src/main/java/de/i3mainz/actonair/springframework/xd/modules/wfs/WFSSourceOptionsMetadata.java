/**
 * 
 */
package de.i3mainz.actonair.springframework.xd.modules.wfs;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.xd.module.options.mixins.PeriodicTriggerMixin;
import org.springframework.xd.module.options.spi.Mixin;
import org.springframework.xd.module.options.spi.ModuleOption;
import org.springframework.xd.module.options.spi.ProfileNamesProvider;

/**
 * @author Nikolai Bock
 *
 */
@Mixin({ PeriodicTriggerMixin.class, SplitterFilterMixin.class,WFSMixin.class})
public class WFSSourceOptionsMetadata implements ProfileNamesProvider {

    private Integer fixedDelay;
    private String cron;
    private String date;
    private String dateFormat = "MM/dd/yy HH:mm:ss";
    private String payload = "";

    @Override
    public String[] profilesToActivate() {
        if (cron != null) {
            return new String[] { "use-cron" };
        } else if (fixedDelay != null) {
            return new String[] { "use-delay" };
        } else {
            return new String[] { "use-date" };
        }
    }

    @Min(0)
    public Integer getFixedDelay() {
        return fixedDelay;
    }

    @AssertTrue(message = "cron and fixedDelay are mutually exclusive")
    private boolean isValid() {
        return !(fixedDelay != null && cron != null);
    }

    @ModuleOption("number of seconds between executions, expressed in TimeUnits (seconds by default)")
    public void setFixedDelay(Integer fixedDelay) {
        this.fixedDelay = fixedDelay;
    }

    public String getCron() {
        return cron;
    }

    @ModuleOption("cron expression specifying when the trigger should fire")
    public void setCron(String cron) {
        this.cron = cron;
    }

    @NotNull
    public String getDate() {
        if (date == null) {
            return new SimpleDateFormat(dateFormat).format(new Date());
        }
        return date;
    }

    @ModuleOption("the date when the trigger should fire")
    public void setDate(String date) {
        this.date = date;
    }

    @NotBlank
    public String getDateFormat() {
        return dateFormat;
    }

    @ModuleOption("the format specifying how the date should be parsed")
    public void setDateFormat(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    public String getPayload() {
        return payload;
    }

    @ModuleOption("the message that will be sent when the trigger fires")
    public void setPayload(String payload) {
        this.payload = payload;
    }
}
