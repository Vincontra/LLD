import time
start = time.perf_counter()
total = 0
for i in range(10_000_000):
    total += (i * i) % 7
end = time.perf_counter()
print(total, f"\n{(end - start) * 1000:.0f} ms")