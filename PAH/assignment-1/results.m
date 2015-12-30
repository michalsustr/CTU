times = [
% fd-autotune       roamer      lama2008
        0.07        0.32        0.38;
        0.71        0.6         0.64;
        6.46        2.29        2.52;
        24.73       8.93        9.84;
        97.12       29          32.36;
];

solutions = [
        870         870         870;
        1225        1225        1225;    
        1470        1470        1470;
        1720        1720        1720;
        1930        1930        1930;
];


figure;
plot(times, 'LineWidth', 3); grid on; legend('fd-autotune', 'roamer', 'lama2008');
title('Search times');
xlabel('Problem instance');
ylabel('Time [s]');

figure;
plot(solutions, 'o', 'LineWidth', 3); grid on; legend('fd-autotune', 'roamer', 'lama2008');
title('Cost of solutions');
xlabel('Problem instance');
ylabel('Cost');
