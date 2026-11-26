#include <iostream>
#include <chrono>
int main() {
    auto start = std::chrono::high_resolution_clock::now();
    long long sum = 0;
    for (long long i = 0; i < 10000000; i++)
        sum += (i * i) % 7;
    auto end = std::chrono::high_resolution_clock::now();
    std::cout << sum << "\n"
              << std::chrono::duration_cast<std::chrono::milliseconds>(end - start).count() << " ms\n";
}