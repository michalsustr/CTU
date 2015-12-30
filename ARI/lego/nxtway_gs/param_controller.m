%% Basic model setup
% Task Sample Rates
ts1 = 0.004;						% ts1 control sample time [sec] !!
ts2 = 0.02;							% ts2 sample time [sec]
ts3 = 0.1;							% ts3 sample time [sec]

% Start Time of balancing and autonomous drive
time_start = 1000;                  % start time of balancing [msec] (= gyro calibration time)
time_auto = 5000;                   % start time of autonomous drive [msec]

% Parameters of Coulombic & Viscous Friction
pwm_gain = 1;						% pwm gain
pwm_offset = 0;						% pwm offset 

% Low Path Filter Coefficients
a_b = 0.8;							% average battery value
a_d = 0.8;							% suppress velocity noise
a_r = 0.996;						% smooth reference signal
a_gc = 0.8;							% calibrate gyro offset
a_gd = 0.999;						% compensate gyro drift
a_theta=0.9;                      % smooth theta signal

% User Setting Values
k_thetadot = 0.3 / R;				% speed gain (0.3 [m/sec])
k_phidot = 25;						% rotation speed gain
k_sync = 0.35;						% wheel synchronization gain
sound_freq = 440;					% sound frequency [Hz]
sound_dur =	500;					% sound duration [msec]
dst_thr = 20;						% distance threshold for obstacle avoidance [cm]
turn_angle = round(60 * W / R);		% right-turn angle in autonomous drive [deg]
gp_max = 100;						% maximum game pad input
log_count = 25;						% data logging count (logging sample time = ts1 * log_count)

%% Controller Parameters 
% system matrices
[A,B,C,D] = ssdata(sys);
% discrete-time model of the system
dsys = c2d(sys,ts1);
[Ad,Bd,Cd,Dd] = ssdata(dsys);
% end of reference step
time_ref_end=12000;

% Here comes the code for  your controller


