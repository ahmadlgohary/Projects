library verilog;
use verilog.vl_types.all;
entity reset_circuit_vlg_check_tst is
    port(
        clr_PC          : in     vl_logic;
        enable_PD       : in     vl_logic;
        sampler_rx      : in     vl_logic
    );
end reset_circuit_vlg_check_tst;
